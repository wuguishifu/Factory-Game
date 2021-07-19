#version 330 core

// input texture coordinate
in vec2 passTextureCoord;

// texture bindings
layout(binding = 0) uniform sampler2D baseMap;

// output color
out vec4 outColor;

void main() {
    outColor = texture(baseMap, passTextureCoord);
}