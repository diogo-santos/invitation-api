package com.company.invitation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private List<String> availableDates;
}