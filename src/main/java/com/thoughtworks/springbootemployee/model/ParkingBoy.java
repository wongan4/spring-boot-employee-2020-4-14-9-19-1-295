package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParkingBoy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickname;
    private Integer employeeId;

    @OneToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id", insertable=false, updatable=false)
    private Employee employee;
}
