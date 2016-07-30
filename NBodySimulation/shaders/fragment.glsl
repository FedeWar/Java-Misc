#version 130

uniform float test;

void main()
{
	gl_FragColor = vec4(test, 0.0, 0.0, 1.0);
}