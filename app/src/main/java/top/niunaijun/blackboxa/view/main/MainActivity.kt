package top.niunaijun.blackboxa.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.util.Log
import top.niunaijun.blackboxa.R
import top.niunaijun.blackboxa.view.list.ListActivity
import top.niunaijun.blackboxa.view.base.LoadingActivity

class MainActivity : LoadingActivity() {

    private val TAG = "MainActivity"

    // Secret pattern: btn3, btn8, btn8, btn1
    private val targetSequence = listOf(R.id.btn3, R.id.btn8, R.id.btn8, R.id.btn1)
    private val recentTaps = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            setupPuzzleGrid()
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate: ${e.message}")
        }
    }

    private fun setupPuzzleGrid() {
        try {
            val buttons = listOf<Button>(
                findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3),
                findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6),
                findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9)
            )

            for (btn in buttons) {
                btn.setOnClickListener { clickedButton ->
                    try {
                        recentTaps.add(clickedButton.id)

                        // Keep only the last 4 taps, instantly dropping older/incorrect history
                        if (recentTaps.size > targetSequence.size) {
                            recentTaps.removeAt(0)
                        }

                        // Unlock silently the second the sequence matches
                        if (recentTaps == targetSequence) {
                            unlockEngine()
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error handling button click: ${e.message}")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in setupPuzzleGrid: ${e.message}")
        }
    }

    private fun unlockEngine() {
        try {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Error in unlockEngine: ${e.message}")
        }
    }

    /**
     * Shows or hides the floating action button based on scroll direction
     * @param show true to show the button (scrolling up), false to hide (scrolling down)
     */
    fun showFloatButton(show: Boolean) {
        try {
            val fab = findViewById<View>(R.id.fab)
            if (show) {
                fab.visibility = View.VISIBLE
            } else {
                fab.visibility = View.GONE
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in showFloatButton: ${e.message}")
        }
    }

    /**
     * Scans the current user and refreshes the app list
     */
    fun scanUser() {
        try {
            Log.d(TAG, "Scanning user")
            // Refresh the user's installed apps or perform any scanning logic here
            // This method is called after app operations complete
        } catch (e: Exception) {
            Log.e(TAG, "Error in scanUser: ${e.message}")
        }
    }
}
