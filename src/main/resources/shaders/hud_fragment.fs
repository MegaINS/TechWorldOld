#version 400

in vec2 outTexCoord;
in vec3 mvPos;
in vec4 outColor;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec3 colour;
uniform int useTexture;

void main()
{

    fragColor = vec4(colour, 1) ;
    fragColor *= outColor;


    if ( useTexture == 1 )
   {
        fragColor *=  texture(texture_sampler, outTexCoord);
   }


}