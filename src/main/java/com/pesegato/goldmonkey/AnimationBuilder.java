/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.goldmonkey;

import java.util.ArrayList;
import model.builders.Builder;
import model.builders.definitions.DefElement;
import model.builders.definitions.Definition;

/**
 *
 * @author Pesegato
 */
public class AnimationBuilder extends Builder {

    public static final String FRAME = "Frame";

    ArrayList<Integer> values=new ArrayList<>();

    public AnimationBuilder(Definition def) {
        super(def);
        for (DefElement de : def.getElements()) {
            switch (de.name) {
                case FRAME:
                    values.add(de.getIntVal());
                    break;
            }
        }
    }

    public int[] buildAnimation(){
        int[] anim=new int[values.size()];
        for (int i=0;i<anim.length;i++)
            anim[i]=values.get(i);
        return anim;
    }

    @Override
    public void readFinalizedLibrary() {
    }
}
