uniform sampler2D m_ColorMap;

#if defined(NEED_TEXCOORD1) 
    varying vec2 texCoord1;
#else 
    varying vec2 texCoord;
#endif


  uniform vec4 m_GlowColor;


void main(){
        #ifdef HAS_GLOWCOLOR
    vec4 color = texture2D(m_ColorMap, texCoord1);
  gl_FragColor =  mix(color, m_GlowColor, 0.5);
gl_FragColor.a = color.a;
        #else
            gl_FragColor = vec4(0.0);
        #endif
}