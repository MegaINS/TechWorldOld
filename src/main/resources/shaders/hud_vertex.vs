#version 400

layout (location=0) in vec3 position;
layout (location=1) in vec4 colors;
layout (location=2) in vec2 texCoord;
layout (location=3) in vec3 vertexNormal;


out vec4 outColor;
out vec2 outTexCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

void main()
{
    outColor = colors;
    gl_Position = projectionMatrix * modelMatrix * vec4(position, 1.0);
    outTexCoord = texCoord;
}