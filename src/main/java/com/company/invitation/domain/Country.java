package com.company.invitation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private Integer attendeeCount;
    private List<String> attendees;
    private String name;
    private String startDate;
}