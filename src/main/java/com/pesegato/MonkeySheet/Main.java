package com.pesegato.MonkeySheet;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        app.start();
    }

    int aniRun[]={10, 1,1,1,1,1,1,1,1,1,1,   -1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int aniBend[]={0, 1,1,1,1,1,1,1,1,1,1};
    
    @Override
    public void simpleInitApp() {
        /*MonkeySheetAppState msa=new MonkeySheetAppState(8, "Models/ab.png");
        msa.addAnim("Run",aniRun);
        msa.addAnim("Bend",aniBend);
        stateManager.attach(msa);
        stateManager.attach(new GeometryAppState());*/
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
