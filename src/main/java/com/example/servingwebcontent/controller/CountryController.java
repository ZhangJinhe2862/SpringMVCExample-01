package com.example.servingwebcontent.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.servingwebcontent.entity.CountryEntity;
import com.example.servingwebcontent.entity.Customer;
import com.example.servingwebcontent.form.CountryForm;
import com.example.servingwebcontent.form.CountrySearchForm;
import com.example.servingwebcontent.repository.CountryEntityMapper;
import com.google.gson.Gson;

@Controller
public class CountryController {

	@Autowired
	private CountryEntityMapper mapper;

	@GetMapping("/country")
	public String init(CountrySearchForm countrySearchForm, CountryForm countryForm) {

		return "country/country";
	}

	/**
	 * Represents a sequence of characters. In this context, it is used to return a
	 * JSON representation of a CountryEntity object.
	 */
	@PostMapping("/country/getCountry")
	@ResponseBody
	public String getCountry(@Validated CountrySearchForm countrySearchForm, CountryForm countryForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		/**
		 * Optional object containing the result of the database query for the country
		 * with the specified country code.
		 */
		Optional<CountryEntity> countryEntity = mapper.selectByPrimaryKey(countrySearchForm.getMstCountryCd());
		if (countryEntity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} else {
			countryForm.setCountryId(countryEntity.get().getMstcountrycd());
			countryForm.setCountryName(countryEntity.get().getMstcountrynanme());
		}

		return new Gson().toJson(countryForm);
	}

	/**
	 * The String class represents character strings.
	 */
	@GetMapping("/list2")
	public String list(Model model, CountryForm countryForm) {
		String name2s = "user2s";
		List<CountryEntity> list2 = mapper.select(SelectDSLCompleter.allRows());
		
		model.addAttribute(name2s, list2);
		return "list";
	}

	/*
	 * 创建一个方法，监听/country/addCountry，
	 * 实现根据请求的参数创建一个CountryEntity对象，并将其插入到数据库中。
	 */
	@PostMapping("/country/addCountry")
	@ResponseBody
	public String addCountry(CountryForm countryForm) {

		CountryEntity addEntity = new CountryEntity();
		addEntity.setMstcountrycd(countryForm.getCountryId());
		addEntity.setMstcountrynanme(countryForm.getCountryName());

		int count = mapper.insert(addEntity);

		Optional<CountryEntity> countryEntity = mapper.selectByPrimaryKey(countryForm.getCountryId());

		countryForm.setCountryId(countryEntity.get().getMstcountrycd());
		countryForm.setCountryName(countryEntity.get().getMstcountrynanme());

		return new Gson().toJson(countryForm);
	}

	@PostMapping("/country/updateCountry")
	@ResponseBody
	public String updateCountry(CountryForm countryForm) {

		CountryEntity updateEntity = new CountryEntity();
		updateEntity.setMstcountrycd(countryForm.getCountryId());
		updateEntity.setMstcountrynanme(countryForm.getCountryName());

		int count = mapper.updateByPrimaryKey(updateEntity);

		Optional<CountryEntity> countryEntity = mapper.selectByPrimaryKey(countryForm.getCountryId());
		if (countryEntity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} else {
			countryForm.setCountryId(countryEntity.get().getMstcountrycd());
			countryForm.setCountryName(countryEntity.get().getMstcountrynanme());
		}

		return new Gson().toJson(countryForm);
	}

	@PostMapping("/country/deleteCountry")
	@ResponseBody
	public String deleteCountry(CountryForm countryForm) {

		int count = mapper.deleteByPrimaryKey(countryForm.getCountryId());

		return new Gson().toJson(countryForm);
	}
}
