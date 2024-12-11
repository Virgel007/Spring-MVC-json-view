package com.example.demo.web.utils;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyData {

    @JsonView(Views.UserSummary.class)
    private Views.UserSummary summary;

    @JsonView(Views.UserDetails.class)
    private Views.UserDetails details;

}
