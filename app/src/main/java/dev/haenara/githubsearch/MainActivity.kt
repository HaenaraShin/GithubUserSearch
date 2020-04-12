package dev.haenara.githubsearch

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.haenara.githubsearch.api.AUTH_TOKEN

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        if (AUTH_TOKEN == "null") {
            Toast.makeText(this, "⚠️ GitHub API 인증 토큰이 없습니다 ⚠️", Toast.LENGTH_SHORT).show()
        }
    }
}
