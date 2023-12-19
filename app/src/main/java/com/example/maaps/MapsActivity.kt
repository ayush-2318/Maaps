package com.example.maaps

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.maaps.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.lang.StringBuilder


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private val itemList: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("test")
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //retriveData()


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        for (i in arraylist)
//        {val sydney = LatLng()
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))}
//    }
        val p1 = LatLng(12.9044, 77.5649)
        val p2 = LatLng(12.9045, 77.5650)
        val p3= LatLng(12.9046, 77.5651)
        val arraylist: ArrayList<LatLng> = ArrayList()
        // on below line we are adding our locations in our array list.
        arraylist.add(p1)
        arraylist.add(p2)
        arraylist.add(p3)
        for (i in arraylist.indices) {
            // below line is use to add marker to each location of our array list.


           // mMap.icon(BitmapDescriptorFactory.fromResource((R.drawable.map_marker))
            mMap.addMarker(MarkerOptions().position(arraylist[i]!!).title("Marker")
                )
            // below line is use to zoom our camera on map.
           // mMap.setLatLngBoundsForCameraTarget(LatLngBounds())
            mMap.setMinZoomPreference(16.0f)
            mMap.setMaxZoomPreference(20.0f)
            mMap.animateCamera(CameraUpdateFactory.zoomTo(300.0f))
            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arraylist[i]))
        }
        mMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            // which is clicked and displaying it in a toast message.
            for (i in arraylist.indices){
                val gmmIntentUri =
                    Uri.parse("google.navigation:q=${arraylist[i]}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)

            }
            val markerName = marker.title
            Toast.makeText(
                this@MapsActivity,
                "Clicked location is $markerName",
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }
//    private fun retriveData(){
//        databaseReference.addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                //itemList.clear() // Clear the list before adding new items
//                for (childSnapshot in dataSnapshot.children) {
//                    val item = childSnapshot.child("humidity").getValue(String::class.java).toString()
//                    item?.let {
//                        //itemList.add(it)
//                        val sb=StringBuilder()
//                        sb.append("$item\n")
//                        binding.tvData.text=sb.toString()
//                        Log.w(TAG, "Failed to read value.")
//                    }
//                }
//                    for (i in itemList){
//
//                    }
//                if(dataSnapshot.exists())
//                { var value=dataSnapshot.getValue<String?>()
//                binding.tvData.text=value!!}
                //Toast.makeText(this@MapsActivity,"inside",Toast.LENGTH_SHORT).show()

                // }
           // }

//            override fun onCancelled(error: DatabaseError) {
//               // Toast.makeText(this@MapsActivity,"ninside",Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "Failed to read value.", error.toException())
//                // Handle any errors
//            }
//        })
  // }
//    }


}

