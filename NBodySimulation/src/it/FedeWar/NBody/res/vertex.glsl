#version 130

uniform mat4 projection;

in vec4 vertex;

void main()
{
	gl_Position = projection * vertex;
	gl_PointSize = 100.0 / gl_Position.z;
}
