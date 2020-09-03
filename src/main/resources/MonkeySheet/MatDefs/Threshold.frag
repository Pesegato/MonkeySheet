uniform sampler2D m_ColorMap;
uniform sampler2D m_Threshold;
float threshold;
uniform float m_Level;
uniform bool m_ShowThreshold;
varying vec2 texCoord;

void main(){

    threshold = texture2D(m_Threshold, texCoord).r;
    if (threshold < m_Level)
        if (m_ShowThreshold == false)
            discard;
        else
            if (m_Level - threshold <0.01)
                gl_FragColor = vec4(1.0, (m_Level - threshold)*100.0, 1.0, texture2D(m_ColorMap, texCoord).a);
            else
                discard;
    else
        gl_FragColor =  texture2D(m_ColorMap, texCoord);
}
