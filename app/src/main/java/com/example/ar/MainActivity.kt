package com.example.ar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import com.example.ar.databinding.ActivityMainBinding
import com.google.ar.core.Config
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var modelNode:ArModelNode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sceneview.apply {
            this.lightEstimationMode=Config.LightEstimationMode.DISABLED
        }
        binding.click.setOnClickListener {
            place()
        }

        modelNode= ArModelNode(binding.sceneview.engine,PlacementMode.INSTANT).apply {
            loadModelGlbAsync(
                glbFileLocation = "models/s.glb",
                scaleToUnits = 1f,
                centerOrigin = Position(-0.5f)
            )
            {
                binding.sceneview.planeRenderer.isVisible=true
            }
            onAnchorChanged ={
                binding.click.isGone=it!=null
            }
        }
        binding.sceneview.addChild(modelNode)
    }
    private fun place(){
        modelNode.anchor()
        binding.sceneview.planeRenderer.isVisible=false
    }
}