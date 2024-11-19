package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import com.workintech.s17d2.model.JuniorDeveloper;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.workintech.s17d2.model.Experience.JUNIOR;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    public Map<Integer, Developer> developers;
    public Taxable taxable;

    @Autowired
    public DeveloperController(Taxable taxable){
        this.taxable=taxable;
    }

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
        developers.put(1, new Developer(1, "Initial Developer", 5000.0, Experience.JUNIOR));
    }



    @GetMapping
    public List<Developer> getDevelopers(){
        return new ArrayList<>(developers.values());
    }
    @GetMapping("/{id}")
    public Developer getDevelopersById(@PathVariable("id")Integer id){
        return developers.get(id);
    }
    public Developer findDeveloper(String developerName){
        for(Developer developer : developers.values()){
            if(developer.getName().equals(developerName)){
                return developer;
            }
        }
        return null;
    }
    public Developer findDeveloper(Integer id){
        for(Developer developer : developers.values()){
            if(developer.getId() == id){
                return developer;
            }
        }
        return null;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Developer addDeveloper(@RequestBody Developer developer){
        double netSalary = developer.getSalary();
        if(developer.getExperience().equals(JUNIOR)){
            netSalary = developer.getSalary() - taxable.getSimpleTaxRate();
            developer.setSalary(netSalary);
        } else if (developer.getExperience().equals(Experience.MID)) {
            netSalary = developer.getSalary() - taxable.getMiddleTaxRate();
            developer.setSalary(netSalary);
        } else if (developer.getExperience().equals(Experience.SENIOR)) {
            netSalary = developer.getSalary() - taxable.getUpperTaxRate();
            developer.setSalary(netSalary);
        }
        else {
            netSalary = developer.getSalary();
        }
        developers.put(developer.getId(), developer);
        return developer;
    }
    @PutMapping("/{id}")
    public Developer updateDeveloper(@PathVariable("id") Integer id, @RequestBody Developer updatedDeveloper){
        Developer oldDeveloper = findDeveloper(id);
        this.developers.replace(id,oldDeveloper,updatedDeveloper);
        return updatedDeveloper;

    }
    @DeleteMapping("/{id}")
    public void deleteDeveloper(@PathVariable("id")Integer id){
        Developer developer = findDeveloper(id);
        developers.remove(developer.getId());
        System.out.println(id + " " + "Id'li developer silinmiştir.");
    }
}

