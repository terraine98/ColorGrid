package top.niunaijun.blackboxa.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import top.niunaijun.blackboxa.R
import top.niunaijun.blackboxa.view.list.ListActivity

class MainActivity : LoadingActivity() {

    // Secret pattern: btn3, btn8, btn8, btn1
    private val targetSequence = listOf(R.id.btn3, R.id.btn8, R.id.btn8, R.id.btn1)
    private val recentTaps = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPuzzleGrid()
    }

    private fun setupPuzzleGrid() {
        val buttons = listOf<Button>(
            findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3),
            findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6),
            findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9)
        )

        for (btn in buttons) {
            btn.setOnClickListener { clickedButton ->
                recentTaps.add(clickedButton.id)
                
                // Keep only the last 4 taps, instantly dropping older/incorrect history
                if (recentTaps.size > targetSequence.size) {
                    recentTaps.removeAt(0)
                }

                // Unlock silently the second the sequence matches
                if (recentTaps == targetSequence) {
                    unlockEngine()
                }
            }
        }
    }

    private fun unlockEngine() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
