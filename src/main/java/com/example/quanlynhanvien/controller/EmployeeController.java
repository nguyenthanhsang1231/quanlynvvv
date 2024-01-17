package com.example.quanlynhanvien.controller;

import com.example.quanlynhanvien.model.Employee;
import com.example.quanlynhanvien.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping()
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("items", employeeRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/rangeSalary")
    public ModelAndView showList(@RequestParam("min") String minSalary,
                                 @RequestParam("max") String maxSalary) {
        ModelAndView modelAndView = new ModelAndView("/list");
        if (minSalary.equals("") || maxSalary.equals("")) {
            modelAndView.addObject("items", employeeRepository.findAll());
            return modelAndView;
        }
        Iterable<Employee> employees = employeeRepository.findAll();
        List<Employee> employees1 = new ArrayList<>();
        for (Employee employee: employees) {
            if (employee.getSalary() >= Integer.valueOf(minSalary) && employee.getSalary() <= Integer.valueOf(maxSalary)) {
                employees1.add(employee);
            }
        }
        modelAndView.addObject("items", employees1);
        return modelAndView;
    }

    @GetMapping("/sortAsc")
    public ModelAndView sortBySalary() {
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("items", employeeRepository.findAllByOrderBySalaryAsc());
        return modelAndView;
    }


    @GetMapping("/add")
    public String add() {
        return "/add";
    }
    @PostMapping("/ass")
    public String ass(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            modelAndView.addObject("employee", employeeOptional.get());
            return modelAndView;
        }
        return null;
    }

    @PostMapping("/edit/{id}")
    public String ass2(@ModelAttribute("employee") Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(name = "name", required = false) String name, Model model) {
        List<Employee> searchResults;
        if (name != null && !name.isEmpty()) {
            searchResults = employeeRepository.findByNameContainsIgnoreCase(name);
        } else {
            searchResults = employeeRepository.findAll();
        }
        model.addAttribute("list", searchResults);
        model.addAttribute("searchTerm", name);

        return "/list";
    }
}
