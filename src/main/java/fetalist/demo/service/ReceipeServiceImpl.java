package fetalist.demo.service;

import fetalist.demo.bodies.CompleteReceipeResponse;
import fetalist.demo.bodies.CreateReceipeBody;
import fetalist.demo.model.Category;
import fetalist.demo.model.Instruction;
import fetalist.demo.model.Receipe;
import fetalist.demo.model.ReceipeIngredient;
import fetalist.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReceipeServiceImpl implements ReceipeService{
    private ReceipeRepository receipeRepository;
    private InstructionRepository instructionRepository;
    private ReceipeIngredientRepository receipeIngredientRepository;
    private UnitRepository unitRepository;
    private CategoryRepository categoryRepository;


    private CompleteReceipeResponse getReceipeById(long id) {
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
    public List<CompleteReceipeResponse> getAllReceipe() {
        List<Receipe> receipes = receipeRepository.findAll();
        List<CompleteReceipeResponse> crr = new ArrayList<>();
        receipes.forEach(r -> crr.add(getReceipeById(r.getId())));
        return crr;
    }

    @Override
    public CompleteReceipeResponse getReceipeById(Long id) {
        Receipe r = receipeRepository.findById(id).orElse(null);
        return r == null ? null : getReceipeById(r.getId());
    }


}
