using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameController : MonoBehaviour
{

    public int Idle;
    public int Locomotion;
    public int Entry;
    public int TurnLeft;
    public int TurnRight;

    void Awake()
    {

        Idle = Animator.StringToHash("Idle");
        Locomotion = Animator.StringToHash("Locomotion");
        Entry = Animator.StringToHash("Entry");
        TurnLeft = Animator.StringToHash("TurnLeft");
        TurnRight = Animator.StringToHash("TurnRight");

    }

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
