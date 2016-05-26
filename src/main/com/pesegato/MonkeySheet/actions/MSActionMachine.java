/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet.actions;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.pesegato.MonkeySheet.MonkeySheetAppState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pesegato
 */
public abstract class MSActionMachine extends AbstractControl {

     static Logger log = LoggerFactory.getLogger(MSActionMachine.class);
 
    MSAction[] actions;
    MSAction currentAction;

    public MSActionMachine(MSAction... actions) {
        this.actions = actions;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (currentAction == null) {
            init();
            nextAction();
        }
        if (MonkeySheetAppState.tTPF == 0) {
            if (currentAction.maybeEnd()) {
                nextAction();
            }
        }
        msUpdate(tpf);
        currentAction.controlUpdate(tpf);
    }

    protected <T extends MSAction> T startAction(Class <T> msActionClass){
        for (MSAction act:actions){
            if (msActionClass.isAssignableFrom(act.getClass())){
                startAction(act);
                return (T)act;
            }
        }
        return null;
    }
    
    private void nextAction() {
        startAction(actions[getNextAction()]);
    }
    
    private void startAction(MSAction action){
        log.trace("start action {}",action);
        if (currentAction==action)
            return;
        currentAction = action;
        currentAction.init(spatial);
    }

    abstract protected void init();

    abstract protected void msUpdate(float tpf);

    abstract protected int getNextAction();

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
