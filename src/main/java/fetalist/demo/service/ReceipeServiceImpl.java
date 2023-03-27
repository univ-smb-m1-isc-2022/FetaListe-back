package fetalist.demo.service;

import fetalist.demo.model.*;
import fetalist.demo.repository.*;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceipeServiceImpl implements ReceipeService{
    private ReceipeRepository receipeRepository;

    @Override
    public Receipe createReceipe(Receipe receipe) {
        return receipeRepository.save(receipe);
    }

    @Override
    public List<Receipe> getAllReceipe() {
        return receipeRepository.findAll();
    }

    @Override
    public Optional<Receipe> getReceipeById(Long id) {
        return receipeRepository.findById(id);
    }


}
