package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the IUNI_DA_CHAIN_STEP_TYPE database table.
 */
@Entity
@Table(name = "IUNI_DA_CHAIN_STEP_TYPE")
public class ChainStepType extends AbstractConfig {

    @Column(name="\"NAME\"")
    private String name;

    //bi-directional many-to-one association to ChainStep
    @OneToMany(mappedBy = "chainStepType")
    private List<ChainStep> chainSteps;

    public ChainStepType() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChainStep> getChainSteps() {
        return chainSteps;
    }

    public void setChainSteps(List<ChainStep> chainSteps) {
        this.chainSteps = chainSteps;
    }
}