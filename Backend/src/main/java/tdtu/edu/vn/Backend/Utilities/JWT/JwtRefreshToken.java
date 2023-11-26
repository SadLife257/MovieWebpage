package tdtu.edu.vn.Backend.Utilities.JWT;

import com.fasterxml.uuid.Generators;

public class JwtRefreshToken {

    public JwtRefreshToken(){

    }

    public String generate(){
        return Generators.randomBasedGenerator().generate().toString();
    }
}
