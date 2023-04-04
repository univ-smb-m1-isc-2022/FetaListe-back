package fetalist.demo.service;

import fetalist.demo.bodies.CompleteReceipeResponse;
import fetalist.demo.bodies.CreateReceipeBody;
import fetalist.demo.model.*;
import fetalist.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReceipeServiceImpl implements ReceipeService{
    private ReceipeRepository receipeRepository;
    private InstructionRepository instructionRepository;
    private ReceipeIngredientRepository receipeIngredientRepository;
    private UnitRepository unitRepository;
    private CategoryRepository categoryRepository;
    private IngredientService ingredientService;


    private CompleteReceipeResponse getCompleteReceipeById(long id) {
        return CompleteReceipeResponse.builder().receipe(receipeRepository.getReferenceById(id))
                .instructions(instructionRepository.findAll(Example.of(Instruction.builder().receipe(receipeRepository.getReferenceById(id)).build())))
                .ri(receipeIngredientRepository.findAll(Example.of(ReceipeIngredient.builder().receipe(receipeRepository.getReferenceById(id)).build()))).build();
    }
    @Override
    public boolean createReceipe(CreateReceipeBody body) {
        Category c = categoryRepository.findBy(
                Example.of(Category.builder().name(body.getCategoryName()).build()),
                FluentQuery.FetchableFluentQuery::first).orElse(categoryRepository.save(Category.builder().name(body.getCategoryName()).build()));
        Receipe r = receipeRepository.save(Receipe.builder()
                .category(c)
                .estimatedTime(body.getEstimatedTime())
                .image(body.getImage())
                .name(body.getName()).build());
        List<ReceipeIngredient> ri = new ArrayList<>();
        body.getRi().forEach(i -> ri.add(ReceipeIngredient.builder()
                .receipe(r).ingredient(i.getIngredient())
                .quantity(i.getQuantity()).unit(unitRepository.findById(i.getUnit().getIdUnit()).get()).build()));
        receipeIngredientRepository.saveAll(ri);
        List<Instruction> il = new ArrayList<>();
        body.getInstructions().forEach(i -> il.add(Instruction.builder().receipe(r).instruction(i).build()));
        instructionRepository.saveAll(il);
        return true;
    }

    @Override
    public List<Receipe> getAllReceipe() {
        return receipeRepository.findAll();
    }

    @Override
    public CompleteReceipeResponse getReceipeById(Long id) {
        Receipe r = receipeRepository.findById(id).orElse(null);
        return r == null ? null : getCompleteReceipeById(r.getId());
    }

    @Override
    public List<Receipe> searchReceipe(String name) {
        List<Receipe> receipes = receipeRepository.findAll();

        if (name == null) {
            return receipes;
        }
        List<Receipe> results = new ArrayList<>();
        Set<String> foundIngs = ingredientService.searchIngredient(name)
                .stream().map(Ingredient::getName)
                .collect(Collectors.toSet());

        for (Receipe receipe : receipes) {
            if (receipe.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(receipe);
                continue;
            }
            Set<String> receipeIngredientIds = receipeIngredientRepository.findAll(Example.of(ReceipeIngredient.builder().receipe(receipe).build()))
                    .stream().map(ReceipeIngredient::getIngredient)
                    .map(Ingredient::getName)
                    .collect(Collectors.toSet());

            if (!Collections.disjoint(receipeIngredientIds, foundIngs)) {
                results.add(receipe);
            }
        }
        return results;
    }


}
