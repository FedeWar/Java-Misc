#version 130

in vec3 vertex;
in vec2 texCoordIn;

out vec2 texCoord;

void main()
{
	gl_Position = vec4(vertex, 1);
	texCoord = texCoordIn;
}