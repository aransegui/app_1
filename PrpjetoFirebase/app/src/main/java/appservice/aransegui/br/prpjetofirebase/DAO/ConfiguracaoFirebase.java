package appservice.aransegui.br.prpjetofirebase.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by luis.aransegui on 20/11/2017.
 */

    public class ConfiguracaoFirebase{

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFireBase()

    {

        if (referenciaFirebase == null) {


            referenciaFirebase = FirebaseDatabase.getInstance().getReference();


        }

        return referenciaFirebase;

    }

            public static FirebaseAuth getFirebaseAutenticacao(){

                if (autenticacao==null){


                    autenticacao=FirebaseAuth.getInstance();
               }

                return autenticacao;


    }


}
