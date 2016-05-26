uniform sampler2D m_ColorMap;
uniform sampler2D m_Threshold;
float threshold;
uniform float m_Level;
varying vec2 texCoord;

void main(){  

    threshold = texture2D(m_Threshold, texCoord).r;
    if (threshold < m_Level)
     discard;

    gl_FragColor =  texture2D(m_ColorMap, texCoord);
//gl_FragColor.a *= threshold; no bono!
}