uniform sampler2D m_ColorMap;
uniform float g_Time;
uniform float m_HitTime;
uniform vec4 m_GlowColor;
vec4 color;
varying vec2 texCoord;

void main(){  

    color = texture2D(m_ColorMap, texCoord);
/*    color.a = color.r * 0.7f;
    vec4 overlay =  vec4(0.5,0.8,1.0,1.0);

 //gl_FragColor = max((1.0 - ((1.0 - color) / overlay)), 0.0);
    gl_FragColor = color * overlay;//
*/
vec4 overlay =  vec4(1.0,1.0,1.0,1.0);
  gl_FragColor =  mix(color, overlay, m_HitTime);//
//gl_FragColor =  m_GlowColor;
gl_FragColor.a = color.a;
}