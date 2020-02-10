package at.fh.swengb.notes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        //sharedPreferences.edit().putString("accessToken",it.token).apply()
        val savedString = sharedPreferences.getString("accessToken", null)

        if (savedString != null) {
            val intent = Intent(this, NotesOverview::class.java)
            startActivity(intent)
            finish()
        }
        button_login.setOnClickListener {

            login()
        }
    }

    private fun login() {
        NotesRepository.login(

            request = AuthRequest(username.text.toString(), password.text.toString()),

            success = {
                val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("accessToken",it.token).apply()
                val intent = Intent(this, NotesOverview::class.java)
                startActivity(intent)
                finish()
            },
            error = {
                Log.e("Error", it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            },
            context = this
        )
    }
}
