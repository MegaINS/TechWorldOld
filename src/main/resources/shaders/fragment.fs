#version 400

uniform sampler2D textureMap;
uniform int useTexture;

in Vertex {
	vec2  texcoord;
	vec3  color;
} Vert;



    void main(){


        gl_FragColor += vec4(1,1,1,1) ;
	    gl_FragColor *= vec4(Vert.color,1);
	    if( useTexture == 1 ){
	        vec4 texture = texture(textureMap, Vert.texcoord);
            if(texture.a<0.1){
        	    discard;
            }
            gl_FragColor *= texture ;
        }
    }


