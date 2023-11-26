package com.example.servingwebcontent.form;

import lombok.Data;

@Data
public class CountryForm {

    private String countryId;
    
    private String countryName;

    public CountryForm() {
    }

    public CountryForm(String countryId, String countryName) {
        this.countryId = countryId;
        
        this.countryName = countryName;
    }

}