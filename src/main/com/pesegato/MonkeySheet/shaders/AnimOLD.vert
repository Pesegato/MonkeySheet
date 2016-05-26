uniform mat4 g_WorldViewProjectionMatrix;
uniform float m_SizeX;
uniform float m_SizeY;
uniform float m_Position;

attribute vec3 inPosition;
attribute vec2 inTexCoord;

varying vec2 texCoord;

void main(){

    float t = m_Position;
    float tPointerY = 1.0 - ((floor(m_Position / m_SizeX)) / m_SizeY) - 1.0 / m_SizeY;
    float tPointerYOffset = (floor(t / m_SizeX)) / m_SizeY;
    float tPointerX = (t - (tPointerYOffset * m_SizeX * m_SizeY)) / m_SizeX;
    texCoord.x = inTexCoord.x / m_SizeX + tPointerX;
    texCoord.y = inTexCoord.y / m_SizeY + tPointerY;


/*
Nehon code

    float t = fract(g_Time) * m_Speed;    
    texCoord.x = inTexCoord.x * m_InvSizeX + floor(t / m_InvSizeX) * m_InvSizeX;   
    float adjust = step(1.0,inTexCoord.x) * step(texCoord.x,floor(texCoord.x ));   
    texCoord.y = (1.0 - inTexCoord.y) * m_InvSizeY + floor(texCoord.x - adjust) * m_InvSizeY ;

*/




   /* if(texCoord.y>1.0 && fract(texCoord.y)<0.5){
        texCoord.y += 2* m_InvSizeY;
    }*/
    //texCoord = inTexCoord;
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
}