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
import demo.news.api.android.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    lateinit var binding: FragmentSignUpBinding

    lateinit var authListner: AuthListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        mAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {

            if (isSubmitReady()) {
                createUser(binding.name.text.toString(), binding.email.text.toString(), binding.password.text.toString())
                Toast.makeText(activity, "Signing up...", Toast.LENGTH_SHORT).show()
                binding.button.isEnabled = false
            }
        }


        return binding.root
    }

    private fun createUser(name: String, email: String, passsword: String) {
        mAuth.createUserWithEmailAndPassword(email, passsword)
                .addOnCompleteListener(activity, { task ->

                    if (task.isSuccessful) {
                        val user = task.result.user
                        authListner.onSignUpSuccessful()

                        if (BuildConfig.DEBUG) {
                            Log.i(TAG, "successfully created user")
                        }
                    } else {
                        Toast.makeText(activity, "Something went wrong: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                        binding.button.isEnabled = true
                    }

                })
    }

    private fun isSubmitReady(): Boolean {
        if (binding.email.text.toString().length == 0) {
            binding.email.error = "Required"
            return false;
        }
        if (binding.name.text.toString().length == 0) {
            binding.name.error = "Required"
            return false;
        }
        if (binding.password.text.toString().length == 0) {
            binding.password.error = "Required"
            return false;

        }
        return true;
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is AuthListener) {
            authListner = context
        }

    }


    companion object {

        val TAG: String = SignUpFragment::class.java.simpleName

        fun newInstance(): SignUpFragment {
            val fragment = SignUpFragment()
            return fragment
        }
    }
}
