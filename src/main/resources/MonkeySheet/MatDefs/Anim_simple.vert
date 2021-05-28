uniform mat4 g_WorldViewProjectionMatrix;
uniform float m_SizeX;
uniform float m_SizeY;
uniform float m_Position;
uniform float m_FlipHorizontal;

attribute vec3 inPosition;
attribute vec2 inTexCoord;
attribute float inTexCoord2;
attribute float inTexCoord3;

varying float vAlpha;
varying vec2 texCoord;

void main(){

    float t = m_Position;
    #ifdef HAS_VERTEXSHEETPOS
    t = inTexCoord2;
    vAlpha = inTexCoord3;
    #endif

    float yAtlas = floor(t / m_SizeY);
    float xAtlas = t - (yAtlas * m_SizeY);
    //texCoord = vec2(xAtlas + inTexCoord.x, yAtlas + inTexCoord.y) / vec2(m_SizeX, m_SizeY);

    texCoord = vec2(xAtlas + inTexCoord.x, (m_SizeY - 1.0 - yAtlas) + inTexCoord.y) / vec2(m_SizeX, m_SizeY);

    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
}
