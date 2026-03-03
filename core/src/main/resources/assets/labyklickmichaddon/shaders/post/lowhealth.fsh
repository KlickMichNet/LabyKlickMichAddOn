#version 150
#l3d_import <labymod:shaders/include/post_processor.glsl>

#ifdef UNIFORM_BLOCK
layout(std140) uniform EffectData {
    float desaturation;
    float bloodIntensity;
};
#else
uniform float desaturation;
uniform float bloodIntensity;
#endif

uniform sampler2D DiffuseSampler;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = TEXTURE(DiffuseSampler, texCoord);

    // Entsättigung
    float gray = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));
    float desatStrength = clamp(desaturation, 0.0, 1.0) * 0.5;
    vec3 result = mix(color.rgb, vec3(gray), desatStrength);

    // Blut-Vignette
    float intensity = clamp(bloodIntensity, 0.0, 1.0);
    vec2 center = texCoord - 0.5;
    float dist = length(center) * 2.0; // 0 in der Mitte, ~1.4 in den Ecken
    float vignette = smoothstep(0.3, 1.5, dist) * intensity;
    vec3 bloodColor = vec3(0.8, 0.0, 0.0);
    result = mix(result, bloodColor, vignette);

    fragColor = vec4(result, color.a);
}