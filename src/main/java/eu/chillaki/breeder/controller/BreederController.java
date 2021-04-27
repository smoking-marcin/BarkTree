package eu.chillaki.breeder.controller;

import eu.chillaki.address.entity.Address;
import eu.chillaki.address.entity.AddressRepository;
import eu.chillaki.address.service.AddressService;
import eu.chillaki.breeder.entity.Breeder;
import eu.chillaki.breeder.entity.BreederRepository;
import eu.chillaki.breeder.service.BreederService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@ComponentScan("eu.chillaki")
@RequestMapping("/breeder")
@RequiredArgsConstructor
public class BreederController {
    private final BreederService breederService;
    private final AddressService addressService;
    private final BreederRepository breederRepository;
    private final AddressRepository addressRepository;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView showBreeder(Model model, @Valid @ModelAttribute("breeder") Breeder breeder, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("breeder/edit", "breeder", breeder);
        }
        if (breeder != null) {
            List<Long> breederIds = breederRepository.findAllMatching(breeder.getName());
            List<Breeder> breederList  = new ArrayList<>();
            for (Long breederId: breederIds) {
                breederList.add(breederService.read(breederId));
            }
            System.out.println("findfunction:");
            System.out.println(breederList.size());
            System.out.println(breederList.toString());
            model.addAttribute("breederList", breederList);
            return new ModelAndView("breeder/show", "breeder", breederList);
        }
        return new ModelAndView("breeder/edit", "breeder", breeder);
    }


    @RequestMapping(value = "/addAddress", method = RequestMethod.GET)
    public ModelAndView addAddress(Model model, @Valid @ModelAttribute("breeder") Breeder breeder, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("breeder/edit", "breeder", breeder);
        }
        if (breeder != null) {
            List<Address> addressList = addressRepository.findAll();
            Map<Address,Long> addressBreederMap = addressList.stream()
                    .collect(
                            Collectors.toMap(address -> address,address -> breeder.getBreederId()));
            return new ModelAndView("breeder/addAddress", "addressBreederMap", addressBreederMap);
        }
        return new ModelAndView("breeder/edit", "breeder", breeder);
    }

    @RequestMapping(value = { "/add/{breederId}/{addressId}"}, method = RequestMethod.POST)
    public ModelAndView add(Model model,@PathVariable String breederId,@PathVariable String addressId) throws NumberFormatException {
        System.out.println("received: "+breederId+" and "+addressId);
        if (breederId != null && addressId != null ) {
            try {
                Long idBreeder = Long.parseLong(breederId);
                Long idAddress = Long.parseLong(addressId);
                Breeder breeder = breederService.read(idBreeder);
                Address address = addressService.read(idAddress);
                System.out.println("I will join: "+breeder+" with "+address);
                breeder.addAddress(address);
                System.out.println("I've joined: "+breeder);
                breederService.update(breeder);
                return new ModelAndView("breeder/edit", "breeder", breeder);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("breeder/edit", "breeder", new Breeder());
    }

    @RequestMapping(value = {"/", "/{breederId}"}, method = RequestMethod.GET)
    public ModelAndView editBreeder(Model model,@PathVariable(required = false) String breederId) throws NumberFormatException {
        if (breederId != null) {
            try {
                Long id = Long.parseLong(breederId);
                Breeder breeder = breederService.read(id);
                System.out.println("I will return: "+breeder.getAddressList());
                ModelAndView edit = new ModelAndView("breeder/edit", "breeder", breeder);
                edit.addObject("addressList",breeder.getAddressList());
                return edit;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("breeder/edit", "breeder", new Breeder());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String update(@Valid @ModelAttribute("breeder")Breeder breeder, BindingResult result) {
        if (result.hasErrors()) {
            return breeder.toString();
        }
        System.out.println("printing breeder: "+breeder+"where breederid: "+breeder.getBreederId());
        if (breeder.getBreederId() == null) {
            breederService.create(breeder);
        } else {
            breederService.update(breeder);
        }
        System.out.println(breeder);
        System.out.println(breeder.getBreederId());
        return breeder.toString();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@Valid @ModelAttribute("breeder")Breeder breeder, BindingResult result) {
        if (result.hasErrors()) {
            return breeder.toString();
        }
        if (breeder.getBreederId() != null) {
            breederService.delete(breeder.getBreederId());
            return breeder.toString();
        }
        return "";
    }

    @RequestMapping(value = "/autocomplete", method = RequestMethod.POST)
    @ResponseBody
    public String autocomplete(@RequestParam(name = "column") String column, @RequestParam(name = "value") String value) {
        List<String> completes = new ArrayList();
        switch(column) {
            case "name":
                completes = breederRepository.findNameMatch(value);
                break;
        }
        if (completes.size() > 0) {
            return String.join(";", completes);
        }
        return "no matches";
    }
}
