/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snw.entity;

import com.snw.persistence.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "REPORT")
public class ReportEntity extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "amount")
    private BigDecimal amount;
}
