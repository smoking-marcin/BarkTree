package eu.chillaki.address.controller;

import eu.chillaki.address.entity.Address;
import eu.chillaki.address.entity.AddressRepository;
import eu.chillaki.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@ComponentScan("eu.chillaki")
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final AddressRepository addressRepository;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView showAddress(Model model,@Valid @ModelAttribute("address")Address address, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("address/edit", "address", address);
        }
        if (address != null) {
            List<Address> addressList = addressRepository.findAllMatching(address.getCountry(),address.getCity(),address.getHouseNumber(),address.getState(),address.getStreetName(),address.getZip());
            System.out.println("findfunction:");
            System.out.println(addressList.toString());
            model.addAttribute("addressList", addressList);
            return new ModelAndView("address/show", "address", addressList);
        }
        return new ModelAndView("address/edit", "address", address);
    }

    @RequestMapping(value = {"/", "/{addressId}"}, method = RequestMethod.GET)
    public ModelAndView editAddress(Model model,@PathVariable(required = false) String addressId) throws NumberFormatException {
        if (addressId != null) {
            try {
                Long id = Long.parseLong(addressId);
                Address address = addressService.read(id);
                System.out.println("I will return: "+address);
                return new ModelAndView("address/edit", "address", address);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("address/edit", "address", new Address());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String update(@Valid @ModelAttribute("address")Address address, BindingResult result) {
        if (result.hasErrors()) {
            return address.toString();
        }
        System.out.println("printing address: "+address+"where addressid: "+address.getAddressId());
        if (address.getAddressId() == null) {
            addressService.create(address);
        } else {
            addressService.update(address);
        }
        System.out.println(address);
        System.out.println(address.getAddressId());
        return address.toString();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@Valid @ModelAttribute("address")Address address, BindingResult result) {
        if (result.hasErrors()) {
            return address.toString();
        }
        if (address.getAddressId() != null) {
            addressService.delete(address.getAddressId());
            return address.toString();
        }
        return "";
    }

    @RequestMapping(value = "/autocomplete", method = RequestMethod.POST)
    @ResponseBody
    public String autocomplete(@RequestParam(name = "column") String column, @RequestParam(name = "value") String value) {
        List<String> completes = new ArrayList();
        switch(column) {
            case "city":
                completes = addressRepository.findCityMatch(value);
                break;
            case "country":
                completes = addressRepository.findCountryMatch(value);
                break;
            case "state":
                completes = addressRepository.findStateMatch(value);
                break;
            case "streetName":
                completes = addressRepository.findStreetNameMatch(value);
                break;
            case "zip":
                completes = addressRepository.findZipMatch(value);
                break;
        }
        if (completes.size() > 0) {
            return String.join(";", completes);
        }
        return "no matches";
    }
}
