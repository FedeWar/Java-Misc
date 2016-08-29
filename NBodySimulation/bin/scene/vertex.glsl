#version 130

uniform mat4 projection;

in vec3 vertex;
in vec2 texCoordIn;

void main()
{
	gl_Position = projection * vec4(vertex, 1);
}