package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam(required = false) String searchTerm) {
        ArrayList<Job> jobs;
        String titleColumnLabel = searchType;

        model.addAttribute("columns", columnChoices);

        if (searchTerm.trim().equalsIgnoreCase("all") || searchTerm.trim().isEmpty()) {
            jobs = JobData.findAll();
        } else if (searchType.equals("all")) {
            jobs = JobData.findByValue(searchTerm.trim().toLowerCase());
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm.trim().toLowerCase());
        }

        if (titleColumnLabel.equalsIgnoreCase("positionType")) {
            titleColumnLabel = "Position Type";
        } else if (titleColumnLabel.equalsIgnoreCase("coreCompetency")) {
            titleColumnLabel = "Skill";
        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("title", "Jobs with " + titleColumnLabel + ": " + searchTerm);
        return "search";
    }

}

