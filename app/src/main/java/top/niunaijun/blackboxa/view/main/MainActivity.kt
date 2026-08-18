package top.niunaijun.blackboxa.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.util.Log
import android.graphics.Color
import top.niunaijun.blackboxa.R
import top.niunaijun.blackboxa.view.list.ListActivity
import top.niunaijun.blackboxa.view.base.LoadingActivity

class MainActivity : LoadingActivity() {

    private val TAG = "MainActivity"

    // Secret pattern: positions 6, 2, 12, 9 (1-indexed in your request)
    // Converting to 0-indexed button indices: 5, 1, 11, 8
    private val targetSequence = listOf(5, 1, 11, 8)
    private val recentTaps = mutableListOf<Int>()
    
    private val colors = listOf(
        Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA,
        Color.CYAN, Color.parseColor("#FF6B6B"), Color.parseColor("#4ECDC4"),
        Color.parseColor("#45B7D1"), Color.parseColor("#FFA07A"), Color.parseColor("#98D8C8"),
        Color.parseColor("#F7DC6F"), Color.parseColor("#BB8FCE"), Color.parseColor("#85C1E2"),
        Color.parseColor("#F8B88B")
    )
    
    private val buttonIds = intArrayOf(
        R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
        R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10,
        R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15
    )
    
    private var isUnlocked = false

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
            val buttons = mutableListOf<Button>()
            for (id in buttonIds) {
                buttons.add(findViewById(id))
            }

            // Assign colors to buttons
            for ((index, btn) in buttons.withIndex()) {
                btn.setBackgroundColor(colors[index])
                
                btn.setOnClickListener {
                    try {
                        if (!isUnlocked) {
                            recentTaps.add(index)

                            // Keep only the last 4 taps, instantly dropping older/incorrect history
                            if (recentTaps.size > targetSequence.size) {
                                recentTaps.removeAt(0)
                            }

                            // Unlock silently the second the sequence matches
                            if (recentTaps == targetSequence) {
                                isUnlocked = true
                                unlockEngine()
                            }
                            // Wrong sequences are ignored with no message
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

    override fun onPause() {
        super.onPause()
        // Reset to tap-tap puzzle when exiting the app
        resetToTapTap()
    }

    private fun resetToTapTap() {
        try {
            recentTaps.clear()
            isUnlocked = false
        } catch (e: Exception) {
            Log.e(TAG, "Error in resetToTapTap: ${e.message}")
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

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
