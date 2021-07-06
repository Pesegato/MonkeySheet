uniform sampler2D m_ColorMap;
uniform float g_Time;
uniform float m_HitTime;
uniform float m_AlphaValue;
uniform vec4 m_FogColor;
uniform float m_FogIntensity;
uniform float m_HueShift;
uniform bool m_GrayScale;
vec4 color;
varying vec2 texCoord;

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
/*
color = texture2D(m_ColorMap, texCoord);

  gl_FragColor = mix(color, m_FogColor, m_FogIntensity);
  gl_FragColor.a = color.a*m_AlphaValue;

*/
//vec3 vHSV = vec3(-100.0, 0.0, 0.0);

    vec4 textureColor = texture2D(m_ColorMap, texCoord);
    vec3 fragRGB = textureColor.rgb;
    vec3 fragHSV = rgb2hsv(fragRGB).xyz;
    fragHSV.x += m_HueShift;
    //fragHSV.x += vHSV.x / 360.0;
    //fragHSV.yz *= vHSV.yz;
    //fragHSV.xyz = mod(fragHSV.xyz, 1.0);
    fragRGB = hsv2rgb(fragHSV);
    gl_FragColor = vec4(fragRGB, textureColor.w);
    if (m_GrayScale) {
        float gray = dot(gl_FragColor.rgb, vec3(0.299, 0.587, 0.114));
        gl_FragColor = vec4(vec3(gray), gl_FragColor.a);
    }
}

