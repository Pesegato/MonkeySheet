uniform mat4 g_WorldViewProjectionMatrix;
uniform float m_SizeX;
uniform float m_SizeY;
uniform float m_Position;
uniform float m_FlipHorizontal;

attribute vec3 inPosition;
attribute vec2 inTexCoord;
attribute float inTexCoord2;

varying vec2 texCoord;

void main(){

    float t = m_Position;
    #ifdef HAS_VERTEXSHEETPOS
        t = inTexCoord2;
    #endif
    float tPointerY = 1.0 - ((floor(t / m_SizeX)) / m_SizeY) - 1.0 / m_SizeY;
    float tPointerYOffset = (floor(t / m_SizeX)) / m_SizeY;
    float tPointerX = (t - (tPointerYOffset * m_SizeX * m_SizeY)) / m_SizeX;
    if (m_FlipHorizontal == 1.0 ) {
        texCoord.x = ( 1.0 - inTexCoord.x ) / m_SizeX + tPointerX;
    }
    else {
        texCoord.x = inTexCoord.x / m_SizeX + tPointerX;
    }
    texCoord.y = inTexCoord.y / m_SizeY + tPointerY;

    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
}
