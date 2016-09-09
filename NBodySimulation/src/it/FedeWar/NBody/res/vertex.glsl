#version 130

uniform mat4 projection;

in vec3 vertex;
in vec2 texCoordIn;

out vec2 onScreen;

void main()
{
	gl_Position = projection * vec4(vertex, 1);
	gl_PointSize = 100.0 / gl_Position.z;
	onScreen = gl_Position.xy;
}
