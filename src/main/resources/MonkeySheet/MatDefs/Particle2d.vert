uniform mat4 g_WorldViewProjectionMatrix;

attribute vec3 inPosition;
attribute vec4 inColor;
attribute vec4 inTexCoord;

varying vec4 color;

#ifdef USE_TEXTURE
varying vec4 texCoord;
#endif

#ifdef POINT_SPRITE
uniform mat4 g_WorldViewMatrix;
uniform mat4 g_WorldMatrix;
uniform vec3 g_CameraPosition;
uniform float m_Quadratic;
const float SIZE_MULTIPLIER = 4.0;
attribute float inSize;
#endif

void main(){
    vec4 pos = vec4(inPosition, 1.0);

    gl_Position = g_WorldViewProjectionMatrix * pos;
    color = inColor;

    #ifdef USE_TEXTURE
        texCoord = inTexCoord;
    #endif

    #ifdef POINT_SPRITE
        gl_PointSize = max(1.0, inSize);

        color.a *= min(gl_PointSize, 1.0);
    #endif
}
