package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the IUNI_DA_CHAIN database table.
 */
@Entity
@Table(name = "IUNI_DA_CHAIN")
public class Chain extends AbstractConfig {

    @Column(name="\"NAME\"")
    private String name;

    private String product;

    //bi-directional many-to-one association to ChainStep
    @OneToMany(mappedBy = "chain")
    private List<ChainStep> chainSteps;

    public Chain() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public List<ChainStep> getChainSteps() {
        return this.chainSteps;
    }

    public void setChainSteps(List<ChainStep> chainSteps) {
        this.chainSteps = chainSteps;
    }

}