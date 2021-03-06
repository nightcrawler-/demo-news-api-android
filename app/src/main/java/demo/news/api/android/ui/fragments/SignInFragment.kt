package demo.news.api.android.ui.fragments


import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import demo.news.api.android.BuildConfig
import demo.news.api.android.R
import demo.news.api.android.databinding.FragmentSignInBinding


/**
 * A simple [Fragment] subclass.
 */
class SignInFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    lateinit var binding: FragmentSignInBinding

    lateinit var authListner: AuthListener


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)

        mAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            if (isSubmitReady()) {
                signIn(binding.email.text.toString(), binding.password.text.toString())
                Toast.makeText(activity, "Signing in...", Toast.LENGTH_SHORT).show()
                binding.button.isEnabled = false
            }
        }
        return binding.root
    }


    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, { task ->

                    if (task.isSuccessful) {
                        val user = task.result.user
                        authListner.onSignInSuccessful()
                        if (BuildConfig.DEBUG) {
                            Log.i(SignUpFragment.TAG, "successfully created user")
                        }
                    } else {
                        binding.button.isEnabled = true
                        Toast.makeText(activity, "Failed :( " +  task.exception?.message, Toast.LENGTH_SHORT).show()
                    }

                })
    }

    private fun isSubmitReady(): Boolean {
        if (binding.email.text.toString().length == 0) {
            binding.email.error = "Required"
            return false
        }
        if (binding.password.text.toString().length == 0) {
            binding.password.error = "Required"
            return false

        }
        return true
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is AuthListener) {
            authListner = context
        }

    }


    companion object {

        fun newInstance(): SignInFragment {
            val fragment = SignInFragment()
            return fragment
        }
    }

}
