#version 400

layout (location=0) in vec3 position;
layout (location=1) in vec3 colors;
layout (location=2) in vec2 textures;
layout (location=3) in vec3 vertexNormal;




uniform vec3 viewPosition;
uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;


out Vertex {
	vec2  texcoord;
	vec3  color;
} Vert;




void main()
{


	Vert.color = colors;
	Vert.texcoord = textures;
	vec4 vertex   = modelViewMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * vertex;
}