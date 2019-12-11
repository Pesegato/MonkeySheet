uniform sampler2D m_ColorMap;
uniform float g_Time;
uniform float m_HitTime;
uniform float m_AlphaValue;
uniform vec4 m_GlowColor;
uniform vec4 m_FogColor;
uniform float m_FogIntensity;
uniform float m_HueShift;
vec4 color;
varying vec2 texCoord;
varying float vAlpha;

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main(){

color = texture2D(m_ColorMap, texCoord);
    vec3 fragRGB = color.rgb;
    vec3 fragHSV = rgb2hsv(fragRGB).xyz;
    fragHSV.x += m_HueShift;
    //fragHSV.x += vHSV.x / 360.0;
    //fragHSV.yz *= vHSV.yz;
    //fragHSV.xyz = mod(fragHSV.xyz, 1.0);
    fragRGB = hsv2rgb(fragHSV);
/*    color.a = color.r * 0.7f;
    vec4 overlay =  vec4(0.5,0.8,1.0,1.0);

 //gl_FragColor = max((1.0 - ((1.0 - color) / overlay)), 0.0);
    gl_FragColor = color * overlay;//
*/
//vec4 overlay =  vec4(1.0,1.0,1.0,1.0);
  //gl_FragColor =  mix(color, overlay, m_HitTime);
    color.rgb=fragRGB;
  gl_FragColor = mix(color, m_FogColor, m_FogIntensity);
//gl_FragColor = color;
//gl_FragColor.r = color.r*m_Pulse;
//gl_FragColor.g = m_Pulse;
//gl_FragColor.g = m_Pulse;
//gl_FragColor =  m_GlowColor;
float t = m_AlphaValue;
    #ifdef HAS_VERTEXSHEETPOS
        t = vAlpha;
    #endif
gl_FragColor.a = color.a*t;
}
