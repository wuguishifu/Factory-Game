#version 460 core

// input values
layout(location = 0) in vec2 vPosition;
layout(location = 1) in vec2 vTextureCoord;

// output values
out vec2 passTextureCoord;

uniform mat4 vModel;
uniform mat4 vView;
uniform mat4 vProjection;

void main() {
    // set the position of this vertex
    gl_Position = vProjection * vView * vModel * vec4(vPosition, 0.0, 1.0);

    // pass the texture coordinate
    passTextureCoord = vTextureCoord;
}