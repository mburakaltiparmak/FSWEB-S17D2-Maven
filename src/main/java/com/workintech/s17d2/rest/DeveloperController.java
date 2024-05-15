package com.workintech.s17d2.rest;

import com.workintech.s17d2.dto.DeveloperResponse;
import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.DeveloperFactory;
import com.workintech.s17d2.model.Experience;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    public Map<Integer, Developer> developers;
    private Taxable taxable;
    @Autowired
    public DeveloperController (Taxable taxable){
        this.taxable=taxable;
    }
    @PostConstruct
    public void init() {
        this.developers = new HashMap<>();
        this.developers.put(1,new Developer(1,"Burak",25000, Experience.JUNIOR));
        System.out.println("developersMap = " + developers);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperResponse addDeveloper(@RequestBody Developer developer){
        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer,taxable);
        if(Objects.nonNull(createdDeveloper)){
            developers.put(createdDeveloper.getId(),createdDeveloper);
        }

        return new DeveloperResponse(createdDeveloper, "Developer added successfully.", HttpStatus.CREATED.value());
    }
    @GetMapping
    public List getDeveloperList(){
        return new ArrayList<>(this.developers.values());
    }
    @GetMapping("/{id}")
    public DeveloperResponse getDeveloperId(@PathVariable("id") Integer id){
        Developer foundDeveloper = this.developers.get(id);
        if(foundDeveloper==null){
            return new DeveloperResponse(null,"developer is not found",HttpStatus.NOT_FOUND.value());
        }
        return new DeveloperResponse(foundDeveloper,"developer is found successfully with an id ",HttpStatus.OK.value());
    }


    @PutMapping("/{id}")
    public DeveloperResponse updateDeveloper(@PathVariable("id") Integer id,@RequestBody Developer developer){
developer.setId(id);
Developer newDeveloper = DeveloperFactory.createDeveloper(developer,taxable);
this.developers.put(newDeveloper.getId(),newDeveloper);
return new DeveloperResponse(newDeveloper,"developer updated successfully",HttpStatus.OK.value());

    }
    @DeleteMapping("/{id}")
    public DeveloperResponse deleteDeveloper(@PathVariable Integer id){
        Developer removedDeveloper = this.developers.get(id);
        this.developers.remove(id);
        return new DeveloperResponse(removedDeveloper,"Developer deleted successfully",HttpStatus.NO_CONTENT.value()) ;
    }

}
