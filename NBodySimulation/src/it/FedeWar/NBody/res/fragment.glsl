#version 450

uniform float test;

in vec2 onScreen;

void main()
{
	// Calcola il gradiente del colore
	float x = gl_PointCoord.x - 0.5f;
	float y = gl_PointCoord.y - 0.5f;
	gl_FragColor = vec4(2.0f, 2.0f, 2.0f, 2.0f) * (0.5f - sqrt(x * x + y * y));
}
